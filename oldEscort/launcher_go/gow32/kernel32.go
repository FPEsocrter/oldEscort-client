package gow32

import (
	"os"
	"syscall"
	"unsafe"
)

var (
	kernel32         = syscall.NewLazyDLL("kernel32.dll")
	procCreateMutex  = kernel32.NewProc("CreateMutexW")
	procReleaseMutex = kernel32.NewProc("ReleaseMutex")
	lockFile         = kernel32.NewProc("LockFile")
	unlockFile       = kernel32.NewProc("UnlockFile")
)

func ReleaseMutex(id uintptr) error {
	_, _, err := procReleaseMutex.Call(
		id,
	)
	switch int(err.(syscall.Errno)) {
	case 0:
		return nil
	default:
		return err
	}
}

func CreateMutex(name string) (uintptr, error) {
	fromString, _ := syscall.UTF16PtrFromString(name)
	ret, _, err := procCreateMutex.Call(
		0,
		0,
		uintptr(unsafe.Pointer(fromString)),
	)
	switch int(err.(syscall.Errno)) {
	case 0:
		return ret, nil
	default:
		return ret, err
	}
}

func LockFile(file *os.File) (bool, error) {
	ret, _, err := lockFile.Call(file.Fd(), 0, 0, 0, 0)
	switch int(err.(syscall.Errno)) {
	case 0:
		return ret != 0, nil
	default:
		return ret != 0, err
	}
}

func UnLockFile(file *os.File) (bool, error) {
	ret, _, err := unlockFile.Call(file.Fd(), 0, 0, 0, 0)
	switch int(err.(syscall.Errno)) {
	case 0:
		return ret != 0, nil
	default:
		return ret != 0, err
	}
}
