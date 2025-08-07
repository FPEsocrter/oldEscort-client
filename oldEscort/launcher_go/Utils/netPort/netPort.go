package netPort

import (
	"net"
	"strconv"
	"strings"
	"time"
)

func findAvailablePort(port uint16) uint16 {
	listen, _ := net.Listen("tcp", "localhost:"+strconv.Itoa(int(port)))
	address := listen.Addr().String()
	err := listen.Close()
	if err != nil {
		_ = listen.Close()
	}
	parts := strings.Split(address, ":")
	i, _ := strconv.Atoi(parts[len(parts)-1])
	return uint16(i)
}

func AvailablePort(ports []uint16) (uint16, string) {
	if len(ports) == 0 || (len(ports) == 1 && ports[0] == 0) {
		port := findAvailablePort(0)
		return port, strconv.Itoa(int(port))
	} else {

		var portChan = make(chan uint16, len(ports))

		for i, port := range ports {
			i := i
			port := port
			go func() {
				time.Sleep(time.Duration(i) * 50 * time.Millisecond)
				report := findAvailablePort(port)
				if report != 0 {
					portChan <- report
				}
			}()
		}

		select {
		case port := <-portChan:
			return port, strconv.Itoa(int(port))
		}

	}
	return 0, "0"
}
