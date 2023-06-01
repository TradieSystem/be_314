"""
Script to combine the two files for entrypoint in github services container for github actions
"""

# open files and read
with open('./src/database/all.sql') as all_sql:
    all_data = all_sql.read()
    print(all_data)

with open('./data.sql') as all_data:
    data_data = all_data.read()

with open('github-entry.sql', 'w') as entry:
    entry.write("{}\n\n".format(all_data))