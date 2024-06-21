import pyautogui
import os


def take_screenshot(screenshot_folder, screenshot_name):
    screenshot_path = os.path.join(screenshot_folder, screenshot_name)
    screenshot = pyautogui.screenshot()
    screenshot.save(screenshot_path)
    return screenshot_path


def get_screenshot_path(screenshot_folder, screenshot_name):
    os.makedirs(screenshot_folder, exist_ok=True)
    screenshot_path = take_screenshot(screenshot_folder, screenshot_name)
    return screenshot_path
