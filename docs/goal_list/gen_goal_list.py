import json
import os
import os.path

import jinja2

_OWN_DIR = os.path.dirname(__file__)
GOALS_ROOT = os.path.abspath(os.path.join(_OWN_DIR, '..', '..', 'src', 'main', 'generated', 'data', 'bingo', 'bingo', 'goals'))
DIFFICULTIES = ['very_easy', 'easy', 'medium', 'hard', 'very_hard']
DIMENSION_TAGS = ['bingo:overworld', 'bingo:nether', 'bingo:end']
SPECIAL_TAGS = ['bingo:never']

def load_goals():
    for difficulty in DIFFICULTIES:
        difficulty_root = os.path.join(GOALS_ROOT, difficulty)
        difficulty_title = difficulty.replace('_', ' ').title()
        if not os.path.isdir(difficulty_root):
            print('no dir:', difficulty_root)
            continue
        with os.scandir(difficulty_root) as scan:
            for entry in scan:
                if not entry.is_file() or not entry.name.endswith('.json'):
                    continue
                with open(entry.path) as f:
                    goal = json.load(f)
                goal['_id'] = entry.name
                goal['_diff'] = difficulty
                goal['_diff_title'] = difficulty_title
                goal['_name'] = entry.name[:-5].replace('_', ' ').title()
                goal['tags'] = sorted(goal.get('tags', []), key=tag_weight)
                yield goal


def tag_weight(tag):
    if tag in SPECIAL_TAGS:
        return 0
    elif tag in DIMENSION_TAGS:
        return 1
    else:
        return 2


def gen_goal_list():
    with open(os.path.join(_OWN_DIR, 'goal_list.jinja2')) as f:
        template = jinja2.Template(f.read())
    html = template.render(goals=load_goals(), dimension_tags=DIMENSION_TAGS, special_tags=SPECIAL_TAGS)
    with open(os.path.join(_OWN_DIR, '..', 'goal_list.html'), 'w') as f:
        f.write(html)


if __name__ == '__main__':
    gen_goal_list()
