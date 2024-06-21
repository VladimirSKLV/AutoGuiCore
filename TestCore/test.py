from py4j.java_gateway import JavaGateway

from path_utils import get_image_path
from utils import get_screenshot_path


def main():
    gateway = JavaGateway()
    app = gateway.entry_point

    screenshot_folder = "actual_screenshots"
    screenshot_name = "openSetting.png"
    screenshot_path = get_screenshot_path(screenshot_folder, screenshot_name)

    app.compareAndClickTopLeft(get_image_path("openKeyMap/setting.png"), get_image_path(screenshot_path),
                               get_image_path("diff.png"), 1920, 1080)

    screenshot_name_2 = "menu.png"
    screenshot_path_2 = get_screenshot_path(screenshot_folder, screenshot_name_2)

    app.compareAndClickTopLeft(get_image_path("openKeyMap/menu.png"), get_image_path(screenshot_path_2),
                               get_image_path("diff_1.png"), 1920, 1080)

    screenshot_name_3 = "plugin.png"
    screenshot_path_3 = get_screenshot_path(screenshot_folder, screenshot_name_3)
    app.compareAndClickCenter(get_image_path("openKeyMap/pjugin2.png"), get_image_path(screenshot_path_3),
                              get_image_path("diff_2.png"), 200, 300, 430, 400)


if __name__ == "__main__":
    main()
