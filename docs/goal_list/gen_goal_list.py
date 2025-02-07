import json
import re
import os
import os.path

import jinja2

_OWN_DIR = os.path.dirname(__file__)
GOALS_ROOT = os.path.abspath(os.path.join(
    _OWN_DIR, '..', '..', 'src', 'main', 'generated', 'data', 'enigmaticsbingogoals', 'bingo', 'goal'
))
SOURCE_ROOT = os.path.abspath(os.path.join(
    _OWN_DIR, '..', '..', 'src', 'main', 'java', 'de', 'rasmusantons', 'enigmaticsbingogoals', 'datagen', 'goal'
))
ORIGINAL_GOALS_ROOT = os.path.abspath(os.path.join(
    _OWN_DIR, '..', '..', 'build', 'bingo', f'bingo-{os.getenv("bingo_commit")}', 'common', 'src', 'main', 'generated',
    'data', 'enigmaticsbingogoals', 'bingo', 'goals'
))
ORIGINAL_SOURCE_ROOT = os.path.abspath(os.path.join(
    _OWN_DIR, '..', '..', 'build', 'bingo', f'bingo-{os.getenv("bingo_commit")}', 'fabric', 'src', 'main', 'java', 'io',
    'github', 'gaming32', 'bingo', 'fabric', 'datagen', 'goal'
))
DIFFICULTIES = ['very_easy', 'easy', 'medium', 'hard', 'very_hard']
DIMENSION_TAGS = ['enigmaticsbingogoals:overworld', 'enigmaticsbingogoals:nether', 'enigmaticsbingogoals:end']
SPECIAL_TAGS = ['enigmaticsbingogoals:never']


def load_todos(difficulty, original):
    camel_case_difficulty = difficulty.replace('_', ' ').title().replace(' ', '')
    if not original:
        goal_source = os.path.join(SOURCE_ROOT, f'Enigmatics{camel_case_difficulty}GoalProvider.java')
    else:
        goal_source = os.path.join(ORIGINAL_SOURCE_ROOT, f'{camel_case_difficulty}GoalProvider.java')
    for line in open(goal_source):
        if m := re.match(r'\s*// ?TODO:? ?(.*)$', line):
            yield {
                '_id': m.group(1),
                '_diff': difficulty,
                'difficulty': difficulty,
                '_diff_title': difficulty.replace('_', ' ').title(),
                '_name': m.group(1),
                'tags': ['todo'],
                'antisynergies': []
            }


def _ensure_list(o):
    return o if isinstance(o, list) else [o]


def load_goals(original):
    for difficulty in DIFFICULTIES:
        difficulty_root = os.path.join(GOALS_ROOT if not original else ORIGINAL_GOALS_ROOT, difficulty)
        difficulty_title = difficulty.replace('_', ' ').title()
        if not os.path.isdir(difficulty_root):
            print('no dir:', difficulty_root)
            continue
        for entry in sorted(os.listdir(difficulty_root)):
            entry_path = os.path.join(difficulty_root, entry)
            if not os.path.isfile(entry_path) or not entry.endswith('.json'):
                continue
            with open(entry_path) as f:
                goal = json.load(f)
            goal['_id'] = entry
            goal['_diff'] = difficulty
            goal['_diff_title'] = difficulty_title
            goal['_name'] = entry[:-5].replace('_', ' ').title()
            goal['tags'] = sorted(_ensure_list(goal.get('tags', [])), key=tag_weight)
            goal['antisynergies'] = sorted(_ensure_list(goal.get('antisynergy', [])))
            goal['catalysts'] = sorted(_ensure_list(goal.get('catalyst', [])))
            goal['reactants'] = sorted(_ensure_list(goal.get('reactant', [])))
            goal['antisynergies'] = sorted(_ensure_list(goal.get('antisynergy', [])))
            if (infrequency := goal.get('infrequency')) is not None:
                goal['infrequency'] = infrequency
            yield goal
        yield from load_todos(difficulty, original)


def tag_weight(tag):
    if tag in SPECIAL_TAGS:
        return 0
    elif tag in DIMENSION_TAGS:
        return 1
    else:
        return 2


def gen_goal_list(original):
    with open(os.path.join(_OWN_DIR, 'goal_list.jinja2')) as f:
        template = jinja2.Template(f.read())
    html = template.render(goals=load_goals(original), dimension_tags=DIMENSION_TAGS, special_tags=SPECIAL_TAGS)
    out_file = os.path.join(_OWN_DIR, '..', 'goal_list.html' if not original else 'original_goal_list.html')
    with open(out_file, 'w') as f:
        f.write(html)


if __name__ == '__main__':
    print(f'{ORIGINAL_GOALS_ROOT=}')
    gen_goal_list(False)
    if os.path.isdir(ORIGINAL_GOALS_ROOT):
        gen_goal_list(True)
