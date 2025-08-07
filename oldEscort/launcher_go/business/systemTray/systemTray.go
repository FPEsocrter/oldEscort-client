package systemTray

import (
	"github.com/getlantern/systray"
	"launcher_go/business/ui"
)

func OnReady() {
	systray.SetIcon(Icon)

	systray.SetTitle("DiyFPBrowse")
	systray.SetTooltip("服务已最小化右下角, 右键点击打开菜单！")

	mShow := systray.AddMenuItem("显示", "显示窗口")
	mHide := systray.AddMenuItem("隐藏", "隐藏窗口")
	systray.AddSeparator()
	mQuit := systray.AddMenuItem("退出", "退出程序")

	go func() {
		for {
			select {
			case <-mShow.ClickedCh:

				ui.Show()
			case <-mHide.ClickedCh:

				ui.Hide()
			case <-mQuit.ClickedCh:
				systray.Quit()
			}
		}
	}()
}
