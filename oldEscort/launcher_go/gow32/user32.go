package gow32

import (
	"syscall"
	"time"
	"unsafe"
)

var (
	user32           = syscall.NewLazyDLL("user32.dll")
	messageBoxW      = user32.NewProc("MessageBoxW")
	showWindowAsync  = user32.NewProc("ShowWindowAsync")
	procShowWindow   = user32.NewProc("ShowWindow")
	findWindow       = user32.NewProc("FindWindowW")
	setWindowPos     = user32.NewProc("SetWindowPos")
	getSystemMetrics = user32.NewProc("GetSystemMetrics")
)

const (
	SW_HIDE          = 0
	SW_RESTORE       = 9
	SW_SHOW          = 5
	HWND_TOPMOST     = ^uintptr(0) // 使用^uintptr(0)表示-1
	HWND_NOTOPMOST   = ^uintptr(1) // 使用^uintptr(1)表示-2
	SW_SHOWMINIMIZED = 2
	SW_SHOWMAXIMIZED = 3
	SM_CXSCREEN      = 0
	SM_CYSCREEN      = 1
)
const (
	MB_ICONINFORMATION = 0x00000040
	MB_OK              = 0x00000000
	SWP_NOSIZE         = 0x0001
	SWP_NOMOVE         = 0x0002
	SWP_NOZORDER       = 0x0004
	SWP_FRAMECHANGED   = 0x0020
	SWP_SHOWWINDOW     = 0x0040
)

func MessageBoxW(title, message string) {

	msg, _ := syscall.UTF16PtrFromString(message)
	titleP, _ := syscall.UTF16PtrFromString(title)
	_, _, _ = messageBoxW.Call(
		0,                               // 父窗口句柄
		uintptr(unsafe.Pointer(msg)),    // 消息文本
		uintptr(unsafe.Pointer(titleP)), // 窗口标题
		MB_ICONINFORMATION|MB_OK)        // 消息框类型

}

func FindWindow(title, class string) (syscall.Handle, error) {
	titlePtr, _ := syscall.UTF16PtrFromString(title)
	classPtr, _ := syscall.UTF16PtrFromString(class)

	hWnd, _, _ := findWindow.Call(uintptr(unsafe.Pointer(classPtr)), uintptr(unsafe.Pointer(titlePtr)))
	if hWnd == 0 {
		return 0, syscall.GetLastError()
	}
	return syscall.Handle(hWnd), nil
}

func ShowWindow(hWnd syscall.Handle) (int, error) {
	r, _, err := procShowWindow.Call(uintptr(hWnd), SW_SHOW)
	if r == 0 {
		return 0, err
	}
	return int(r), nil
}
func RestoreWindow(hWnd syscall.Handle) (int, error) {
	r, _, err := showWindowAsync.Call(uintptr(hWnd), SW_RESTORE)
	if r == 0 {
		return 0, err
	}
	return int(r), nil
}

func HideWindow(hWnd syscall.Handle) (int, error) {
	r, _, err := procShowWindow.Call(uintptr(hWnd), SW_HIDE)
	if r == 0 {
		return 0, err
	}
	return int(r), nil
}
func GetSystemMetrics() (int, int, error) {
	width, _, err := getSystemMetrics.Call(SM_CXSCREEN)
	height, _, _ := getSystemMetrics.Call(SM_CYSCREEN)
	return int(width), int(height), err
}

func SetWindowSize(hWnd syscall.Handle, width, height int) (int, error) {
	ret, _, err := setWindowPos.Call(
		uintptr(hWnd),
		0,
		0,
		0,
		uintptr(width),
		uintptr(height),
		uintptr(SWP_NOMOVE|SWP_NOZORDER|SWP_FRAMECHANGED|SWP_SHOWWINDOW),
	)
	if ret == 0 {
		return 0, err
	}
	return int(ret), nil
}
func SetWindowTopMost(hwnd syscall.Handle) (int, error) {
	ret, _, err := setWindowPos.Call(
		uintptr(hwnd),
		HWND_TOPMOST,
		0,
		0,
		0,
		0,
		uintptr(SWP_NOSIZE|SWP_NOMOVE|SWP_SHOWWINDOW),
	)
	if ret == 0 {
		return 0, err
	}
	return int(ret), nil
}

func SetWindowNotTopMost(hwnd syscall.Handle) (int, error) {
	ret, _, err := setWindowPos.Call(
		uintptr(hwnd),
		HWND_NOTOPMOST,
		0,
		0,
		0,
		0,
		uintptr(SWP_NOMOVE|SWP_NOSIZE),
	)
	if ret == 0 {
		return 0, err
	}
	return int(ret), nil
}

func SetWindowSizAndTop(hWnd syscall.Handle, x, y, width, height int) (int, error) {
	ret, _, err := setWindowPos.Call(
		uintptr(hWnd),
		HWND_TOPMOST,
		uintptr(x),
		uintptr(y),
		uintptr(width),
		uintptr(height),
		uintptr(SWP_FRAMECHANGED|SWP_SHOWWINDOW),
	)
	go func() {
		time.Sleep(300)
		_, _ = SetWindowNotTopMost(hWnd)

	}()
	if ret == 0 {
		return 0, err
	}
	return int(ret), nil
}
