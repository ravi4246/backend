import os
import re

directory = r'c:\Users\raviv\AndroidStudioProjects\MyApplication7\app\src\main\java\com\simats\siddha\myapplication'

for root, _, files in os.walk(directory):
    for fn in files:
        if fn.endswith('.kt'):
            path = os.path.join(root, fn)
            with open(path, 'r', encoding='utf-8') as f:
                content = f.read()
            if 'dummyApiService = object :' in content and 'override suspend fun updateProfile' not in content:
                # Use regex to insert updateProfile after getProfile, ignoring newlines/spaces in getProfile
                new_content = re.sub(
                    r'(override\s+suspend\s+fun\s+getProfile\s*\(\)\s*=\s*retrofit2\.Response\.success\([^)]+\))',
                    r'\1\n            override suspend fun updateProfile(profile: com.simats.siddha.myapplication.api.ProfileDto) = retrofit2.Response.success(profile)',
                    content
                )
                with open(path, 'w', encoding='utf-8') as f:
                    f.write(new_content)
                print('Patched', fn)
