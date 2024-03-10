## Working with the menu

### Description 

In a project with a weather forecast, use the menu to switch the language

![Project basis from class](https://github.com/ipetrushin/MenuDemo)

To switch localization you can use ![example from article](https://medium.com/swlh/android-app-specific-language-change-programmatically-using-kotlin-d650a5392220)



```
Locale locale = new Locale("ru");
Locale.setDefault(locale);
Configuration config = getBaseContext().getResources().getConfiguration();
config.locale = locale;
getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
```


### Results

![изображение](https://github.com/mrglaster/ISU-HW-MobileDev/assets/50916604/1cc67b33-9cac-48f1-82c2-a1bc0f448d7d)  ![изображение](https://github.com/mrglaster/ISU-HW-MobileDev/assets/50916604/6f779a2f-4a7c-413d-b8ea-227001df9217)    ![изображение](https://github.com/mrglaster/ISU-HW-MobileDev/assets/50916604/01936184-a3ab-406c-8711-19a7a5004464)


