import os


def get_image_paths(expected_image, actual_image):
    current_dir = os.path.dirname(os.path.abspath(__file__))
    expected_image_path = os.path.join(current_dir, expected_image)
    actual_image_path = os.path.join(current_dir, actual_image)
    return expected_image_path, actual_image_path


def get_image_path(path):
    current_dir = os.path.dirname(os.path.abspath(__file__))
    image_path = os.path.join(current_dir, path)
    return image_path
