# Pruqa Match Maker
Pruqa Match Maker - Copyright (c) 2018 by Paolo Amosso
License: GNU Affero General Public License v3.0

Pruqa Match Maker is an open source microservice infrastructure for match making input players

The infrastructure is built using Spring Boot + Spring Cloud, the communication is done via Rest and RabbitMq
and the repository is done via MariaDB and MongoDB.

The current algorithm define that given any input player with a map of attributes and a game with a map of rules,
the player will be enriched with a total score that is give from the multiplication of its attributes and the game rules.
Once each player has a total score the service tries to match it, using boundaries defined in absolute or relative values
in the game settings, with any player that is inside that boundaries and is the closest to the first player.
At each retry, with a configurable time, the player's boundaries are multiplied for the number of tentatives, defined in the game settings.
The client will be then notified each time a player has found a match or if there are no matching players (the number
of tentatives exceed the limit defined in the settings).

How to use this:

**Register the Company for the Oauth2 Token**

`
curl -X POST \
  http://localhost:8080/security/register \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 8e264ee9-fc97-4553-8200-c807783f23db' \
  -H 'cache-control: no-cache' \
  -d '{
  "active": 1,
    "companyEmail": "user@user",
    "companyId": 0,
    "companyName": "user",
    "password": "user"
}'
`

**Authorize the session by requesting the Oauth2 Bearer Token**

`
curl -X POST \
  http://localhost:8080/security/oauth/token \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -H 'Postman-Token: ea82a71f-e7aa-4688-b898-10c47f389d8b' \
  -H 'cache-control: no-cache' \
  -d 'username=test%40test&password=pass&grant_type=password'
`

**Add a game to the service**

`
curl -X POST \
  http://localhost:8080/settings/api/v1/public/games/add \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: cf7edc2a-7184-496e-9a06-855222a49908' \
  -H 'cache-control: no-cache' \
  -H 'requestId: 1234' \
  -H 'sessionId: 1234' \
  -d '{
  "gameName": "APEX",
  "gameSetting": {
    "absoluteRange": 0,
    "attributes": [
      {
        "absoluteRange": 0,
        "attributeMultiplier": 1.14,
        "attributeName": "level",
        "incrementalRounds": 0,
        "incrementalWaitTime": 0,
        "percentageRange": 0
      },
      {
        "absoluteRange": 0,
        "attributeMultiplier": 0.5,
        "attributeName": "intelligence",
        "incrementalRounds": 0,
        "incrementalWaitTime": 0,
        "percentageRange": 0
      }
    ],
    "incrementalRounds": 5,
    "incrementalWaitTime": 10000,
    "matchMode": "COUNTER",
    "percentageRange": 0.10
  },
  "responseEndpoint": "localhost:20000/matchmaking/v1/result"
}'
`

**Add a player to the service**

`
curl -X POST \
  http://localhost:8080/starter/api/v1/public/players/add \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 98038ac0-2381-40fb-b467-53f56fc6065d' \
  -H 'cache-control: no-cache' \
  -d '{
  "characteristics": {
    "intelligence": "8",
    "level": ""
  },
  "gameName": "APEX",
  "playerId": "6"
}'
`

The infrastructure is built as below:
![Infrastructure design](https://github.com/Eisman111/Pruqa-Match-Maker/blob/master/matchmakerv3.png)

_There are still many things to add, fix and improve, those will be done based on requests.
Dockerization and deploy could be achieved through Jenkins_