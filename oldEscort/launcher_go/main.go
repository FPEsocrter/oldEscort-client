package main

import (
	"github.com/getlantern/systray"
	"launcher_go/business/businessSystem"
	"launcher_go/business/program"
	"launcher_go/business/rpc"
	"launcher_go/business/share"
	"launcher_go/business/staticServe"
	"launcher_go/business/systemTray"
	"launcher_go/business/ui"
)

//go:generate goversioninfo
func main() {
	otherRun := program.IsOtherRun()
	args := program.GetArgs()
	if !otherRun {

		webPort := businessSystem.Run()
		staticPort := staticServe.Run(webPort)
		info := share.Info{
			WebPort:    webPort,
			StaticPort: staticPort,
		}
		share.Write(info)
		ui.Run(staticPort)
		if id, ok := args["key1"]; ok {
			rpc.OpenEvn(id, info)
		}
		systray.Run(systemTray.OnReady, onExit)
	} else {
		if len(args) == 0 && otherRun {
			read := share.OneRead()
			rpc.ShowUI(read)
		}
	}
	if !otherRun {
		//启动系统托盘

	}

}

/*
*
wmic process get Caption,ProcessId,CommandLine,WorkingSetSize
第一次运行启动 看看有没有老的进程存活 有的话直接kill了 使用
*/
func onExit() {
	share.Delete()
	ui.Kill()
	businessSystem.Kill()

}
