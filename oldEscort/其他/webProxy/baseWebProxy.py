import subprocess
import os

process = None  # 全局变量

proxy_type = {
    1: 'http',
    2: 'https',
    3: 'socks5',
    4: 'ssh',
}


def proxyLauncher(proxy):
    """
    根据代理参数生成命令行参数列表
    """
    web_type, host, port, account, password = (
        proxy['type'], proxy['host'], proxy['port'], proxy['account'], proxy['password'])
    account = account if account is not None else ""
    password = password if password is not None else ""
    if account == "" and password == "":
        return ['-F', '%s://%s:%s' % (proxy_type[web_type], host, port)]

    return ['-F', '%s://%s:%s@%s:%s' % (proxy_type[web_type], account, password, host, port)]


def runWebProxy(webProxyParameters):
    print("runWebProxy")
    """
    启动Web代理进程
    """
    exe_path = "gost.exe"  # 你的.exe文件路径
    if not os.path.exists(exe_path):
        print(f"The file {exe_path} does not exist.")
        return False
    type_value = webProxyParameters['type']
    if type_value == 0:
        return True
    mapPort_value = webProxyParameters['mapPort']
    global process  # 声明process为全局变量

    mapPort_value_arguments = ["-L", ":" + str(mapPort_value)]  # 传递给.exe的参数，可以根据需要修改
    proxy_arguments = proxyLauncher(webProxyParameters)
    command = [exe_path] + mapPort_value_arguments + proxy_arguments
    print(command)
    try:
        # process = subprocess.Popen(command, creationflags=subprocess.CREATE_NEW_CONSOLE)
        process = subprocess.Popen(command, shell=False, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)

        return True
    except FileNotFoundError:
        print(f"找不到文件: {exe_path}")
        return False
    except Exception as e:
        print(f"启动时出错: {e}")
        return False


def kill():
    """
    终止Web代理进程
    """
    global process  # 声明process为全局变量
    if process:
        process.kill()
        process.wait()  # 等待进程结束

# if __name__ == '__main__':
#     webProxyParameters = {
#         'type': 3,
#         'host': '127.0.0.1',
#         'port': 7890,
#         'account': None,
#         'password': None,
#         'mapPort': 8089,
#         'other': {}
#     }
#     if runWebProxy(webProxyParameters):
#         import time
#         time.sleep(20)
#         kill()
