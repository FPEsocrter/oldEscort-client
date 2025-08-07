import asyncio
import httpx
import uuid
import json
import re


async def asyncHttpUrl(url, inetAddress, headers=None):
    address = inetAddress['address']
    port = inetAddress['port']
    webProxyType = inetAddress['webProxyType']
    if headers is None:
        headers = {
            "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
            "Accept-Encoding": "gzip, deflate, br",
            "Accept-Language": "fi",
            "Cache-Control": "max-age=0",
            "Sec-Ch-Ua": "\"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"",
            "Sec-Ch-Ua-Mobile": "?0",
            "Sec-Ch-Ua-Platform": "\"Windows\"",
            "Sec-Fetch-Dest": "document",
            "Sec-Fetch-Mode": "navigate",
            "Sec-Fetch-Site": "none",
            "Sec-Fetch-User": "?1",
            "Upgrade-Insecure-Requests": "1",
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36"
        }

    proxies = None
    if webProxyType != 0:
        proxy_url = "http://%s:%s" % (address, port)
        proxies = {"http://": proxy_url, "https://": proxy_url}

    try:
        async with httpx.AsyncClient(proxies=proxies) as client:
            response = await client.get(url, headers=headers)
            response.raise_for_status()
            return response.text
    except httpx.RequestError as e:
        print(f"启动时出错: {e}")
    return ""


def HttpUrl(url, inetAddress, headers=None):
    loop = asyncio.new_event_loop()
    asyncio.set_event_loop(loop)
    return loop.run_until_complete(asyncHttpUrl(url, inetAddress, headers))


async def get_ip_address(url, inetAddress):
    text = await asyncHttpUrl(url, inetAddress)
    ip_pattern = r'\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}'
    ip_match = re.search(ip_pattern, text)
    if ip_match:
        print("get_ip_address %s" % url)
        return ip_match.group()
    return None


async def getIp(inetAddress):
    tasks = [
        asyncio.create_task(get_ip_address("https://myip.ipip.net/", inetAddress)),
        asyncio.create_task(get_ip_address("https://ipinfo.io/ip", inetAddress))
    ]
    done, pending = await asyncio.wait(tasks, return_when=asyncio.FIRST_COMPLETED)
    for task in done:
        ip = await task
        return [ip]
    return [""]


def getIpInfoUrl(ip, inetAddress):
    headers = {
        'authority': 'ipinfo.io',
        'accept': '*/*',
        'accept-language': 'zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6',
        'cache-control': 'no-cache',
        'content-type': 'application/json',
        'pragma': 'no-cache',
        'referer': 'https://ipinfo.io/',
        "Sec-Ch-Ua": "\"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"",
        'sec-ch-ua-mobile': '?0',
        'sec-ch-ua-platform': 'Windows',
        'sec-fetch-dest': 'empty',
        'sec-fetch-mode': 'cors',
        'sec-fetch-site': 'same-origin',
        'user-agent': "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36"
    }
    url = 'https://ipinfo.io/widget/demo/%s' % ip
    txt = HttpUrl(url=url, inetAddress=inetAddress, headers=headers)
    if len(txt) < 200:
        print(txt)
        return None
    try:
        ipInfo = json.loads(txt)
        data = ipInfo["data"]
        city = data["city"]
        region = data["region"]
        country = data["country"]
        timezone = data["timezone"]
        latitude, longitude = data["loc"].split(',')

    except Exception as e:
        print(e)
        return None
    ipDetails = {
        "ips": [ip],
        "city": city,
        "region": region,
        "countryCode": country,
        "latitude": latitude,
        "longitude": longitude,
        "timezone": timezone
    }
    return ipDetails


def apiIpInfo(ip, inetAddress):
    apiKeys = [
        "532a907f378b70",
        "aa353dfb58ce78",
        "8140e25152245b",
        "28420baf25aad8",
        "93e9caceccd147",
        "4a0b486e502f3e",
        "29db01dd1ccdd2",
        "80ed85adb98517"

    ]
    hex_string = uuid.UUID(int=uuid.getnode()).hex[-12:]
    hex_pairs = [hex_string[i:i + 2] for i in range(0, len(hex_string), 2)]
    decimal_sum = sum(int(pair, 16) for pair in hex_pairs)
    tmp = 1
    tIndex = decimal_sum % len(apiKeys)
    while True:
        city = ""
        region = ""
        country = ""
        timezone = ""
        latitude = ""
        longitude = ""

        key = apiKeys[tIndex]
        url = "https://ipinfo.io/%s?token=%s" % (ip, key)  # todo
        text = HttpUrl(url, inetAddress)
        if text is not None:
            try:
                ipInfo = json.loads(text)
                city = ipInfo["city"]
                region = ipInfo["region"]
                country = ipInfo["country"]
                timezone = ipInfo["timezone"]
                latitude, longitude = ipInfo["loc"].split(',')
            except Exception as e:
                print(e)
                text = None
        if text is None:
            if tmp >= len(apiKeys):
                return None
            tIndex = (tIndex + 1) % len(apiKeys)
            tmp = tmp + 1
            continue

        ipDetails = {
            "ips": [ip],
            "city": city,
            "region": region,
            "countryCode": country,
            "latitude": latitude,
            "longitude": longitude,
            "timezone": timezone
        }
        return ipDetails

    return None


def getInfoByIp(ips, inetAddress):
    ipDetails = getIpInfoUrl(ips[0], inetAddress)
    if ipDetails is not None:
        return ipDetails
    ipDetails = apiIpInfo(ips[0], inetAddress)
    if ipDetails is not None:
        return ipDetails
    return {"ips": []}


def kill():
    print("初始化kill")
    return True


def getIpInfo(inetAddress):
    print("初始化_getIpInfo %s" % inetAddress)
    ips = asyncio.run(asyncio.wait_for(getIp(inetAddress), timeout=60))
    print("current ip %s" % ips)
    if len(ips) == 0 or (len(ips) == 1 and ips[0] is None):
        print("ip是空的")
        return {'ip': []}
    return getInfoByIp(ips, inetAddress)
