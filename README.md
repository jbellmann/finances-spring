
## Import data from Moneydance

#### Export JSON file from Moneydance
- Menu _File_ → _Export_
- _Format_ = _Raw JSON_
- Click _OK_
- Choose destination and click _Save_ 

#### Import Moneydance JSON into finances server

Example using `curl`:
```bash
curl \
 -F "book=Mustermann's Personal Finances" \
 -F "forceReset=true" \
 -F "file=@/Users/mustermann/Documents/Moneydance-JSON-output.json" \
 http://localhost:8080/import/moneydance/json
```
