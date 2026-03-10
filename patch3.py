import os

directory = r'c:\Users\raviv\AndroidStudioProjects\MyApplication7\app\src\main\java\com\simats\siddha\myapplication'
bad_str = """            override suspend fun updateProfile(profile: com.simats.siddha.myapplication.api.ProfileDto) = retrofit2.Response.success(profile)\n            )"""
good_str = """            )\n            override suspend fun updateProfile(profile: com.simats.siddha.myapplication.api.ProfileDto) = retrofit2.Response.success(profile)"""

for root, _, files in os.walk(directory):
    for fn in files:
        if fn.endswith('.kt'):
            path = os.path.join(root, fn)
            with open(path, 'r', encoding='utf-8') as f:
                content = f.read()
            if bad_str in content:
                new_content = content.replace(bad_str, good_str)
                with open(path, 'w', encoding='utf-8') as f:
                    f.write(new_content)
                print('Fixed', fn)
