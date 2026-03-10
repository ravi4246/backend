import os
import glob

directory = r"c:\Users\raviv\AndroidStudioProjects\MyApplication7\app\src\main\java\com\simats\siddha\myapplication"
files = glob.glob(os.path.join(directory, "*.kt"))

search_str = '            override suspend fun getProfile()'
replace_str = '            override suspend fun refreshToken(request: com.simats.siddha.myapplication.api.RefreshTokenRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.TokenResponse("", ""))\n' + search_str

for file in files:
    with open(file, 'r', encoding='utf-8') as f:
        content = f.read()
    
    if search_str in content and "refreshToken" not in content:
        content = content.replace(search_str, replace_str)
        with open(file, 'w', encoding='utf-8') as f:
            f.write(content)
        print("Fixed", os.path.basename(file))
