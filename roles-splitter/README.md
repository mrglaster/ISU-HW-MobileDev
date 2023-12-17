## Split the play by roles

### Description 

You are given a list of roles and a play script in the form of an array of lines.

Each line of the play's script is given as follows:

```Role: text```

The text can contain any characters.

Write a method that will group lines by role, number them and return the result as finished text (see example). Each group is printed as follows:

```
Role:
i) text
j) text2
...
==line feed==
```

i and j are line numbers in the script. Indexing of lines starts from one; groups should be displayed in accordance with the order of roles. Line breaks between groups are required; line breaks at the end of the text are not taken into account.

Note that you have to process a huge play of 50,000 lines for 10 roles - accordingly, incorrect assembly of the resulting line may exceed the time limit.


***Pay attention to a few more nuances***:

1) A character's name can appear more than once on a line, including with a colon;
2) The name of one role may be a prefix of the name of another role (for example, "Luka" and "Luka Lukic");
3) A role that has no replicas must also be present in the output file;
4) The '\n' character must be used as a line feed (UNIX-style line feed);
5) Be careful not to add extra spaces at the end of lines.


***Example of Source Data***:

```
roles:
Городничий
Аммос Федорович
Артемий Филиппович
Лука Лукич
textLines:
Городничий: Я пригласил вас, господа, с тем, чтобы сообщить вам пренеприятное известие: к нам едет ревизор.
Аммос Федорович: Как ревизор?
Артемий Филиппович: Как ревизор?
Городничий: Ревизор из Петербурга, инкогнито. И еще с секретным предписаньем.
Аммос Федорович: Вот те на!
Артемий Филиппович: Вот не было заботы, так подай!
Лука Лукич: Господи боже! еще и с секретным предписаньем!
```


### Results


![изображение](https://github.com/mrglaster/ISU-HW-MobileDev/assets/50916604/bb82de01-4305-4248-b7e9-28550e0aca4e)


