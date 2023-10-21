# anti-phishing-app

App provides EP /verify with method POST, which requires body of following structure:
```json
{
“sender”: “234100200300”,
“recipient”: “48700800999”,
“message”: “Dzień dobry. W związku z audytem nadzór finansowy w naszym banku proszą o potwierdzanie danych pod adresem: https://www.m-bonk.pl.ng/personal-data”
}
```

Person can subscribe to the service by sending SMS "START" to service number.
Person can unsubscribe to the service by sending SMS "STOP" to service number.

Service number is set to ```7777777``` by default, can be changed by setting property ```service.number``` or changing ```application.yaml``` file.
If person subscribed to the service, every message is checked for presence of URL. If URL is found, there will be database lookup in first place. If URL is not known yet (not in DB) a call to external API is executed.

You can start app by executing ```docker compose up``` in project's root directory.