import os
import glob
import re

directory = r"c:\Users\raviv\AndroidStudioProjects\MyApplication7\app\src\main\java\com\simats\siddha\myapplication"
files = glob.glob(os.path.join(directory, "*.kt"))

for file in files:
    with open(file, 'r', encoding='utf-8') as f:
        content = f.read()
    
    original_content = content
    
    if "override suspend fun login" in content and "refreshToken" not in content:
        # For single line expression bodies
        content = re.sub(
            r"(override suspend fun login\(credentials:.*?\).*?TokenResponse\([^\)]*\)\))(\s*\n)",
            r"\1\2            override suspend fun refreshToken(request: com.simats.siddha.myapplication.api.RefreshTokenRequest) = retrofit2.Response.success(com.simats.siddha.myapplication.api.TokenResponse(\"\", \"\"))\2",
            content
        )

    if content != original_content:
        with open(file, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"Fixed {os.path.basename(file)}")
