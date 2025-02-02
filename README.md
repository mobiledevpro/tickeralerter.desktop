# Ticker Alerter Desktop App 
## Built with Kotlin & Compose Multiplatform

![github-preview](https://github.com/user-attachments/assets/55c4ea47-a252-4884-aa22-1be2c38ccb35)

Connected to Binance Futures API and get real-time data for available tickers.

Web Sockets DOC https://github.com/binance/binance-spot-api-docs/blob/master/web-socket-streams.md

```kotlin
object BinanceSocketClientFactory {

    // ......

    const val TEST_URL = "stream.binancefuture.com"
    const val PROD_URL = "fstream.binance.com"
}
```

Rest API DOC https://developers.binance.com/docs/derivatives/usds-margined-futures/general-info

```kotlin
object BinanceHTTPClientFactory {

    // ......

    const val TEST_URL = "testnet.binancefuture.com"
    const val PROD_URL = "fapi.binance.com"
}

```
##
### How to run the project on your local machine:

* You should be registered on Binance Crypto Exchange and create API key - [Register and claim 100USD](https://www.binance.com/activity/referral-entry/CPA?ref=CPA_00XL76XLRC)
* How to create API keys https://www.binance.com/en/support/faq/360002502072
* Create a new file ```key.properties``` in the root of the project
* Add the following lines to the file:
  ```
  api.key.testnet= [your key]
  api.key.live= [your key]
  api.secret.testnet= [your key]
  api.secret.live= [your key]
  ```
* Run the project ````./gradlew :desktop:run````

##
### Roadmap

* [x] Connect to Binance API WebSocket
* [x] Getting all available tickers from API
* [x] Subscribe to tickers to getting update from WebSocket
* [x] On selecting ticker in UI get historical data and save it into local database
* [x] Draw chart by historical data
* [x] Simple alerts by price change (crossing, crossing up, crossing down)
* [ ] EMA Ribbon strategy for Alerts
* [ ] Real-time Chart by selected timeframe
* [ ] Timeframe picker for Chart (5m, 15m, 30m, 1h, 4h, 1d, 1m)
* [ ] Backtesting for selected period (date from - date to)
* ~~[ ] Think about strategy RSI + MACD ?~~
* [ ] Calculate RSI and display for selected ticker
* [ ] Calculate MACD
* [ ] Getting all opened positions
* [ ] Display current PL
* [ ] Ability to run backtest for selected ticker + time frame + period

##
### Modules:

- `:common:utils` - just some useful helpers
- `:common:database` - SQLDelight database definition
- `:desktop` - Desktop application
- `:feature:main` - the main screen
- `:feature:chart`
- `:feature:tickerlist`
- `:feature:tradinglog`

##
### Running desktop application

* To run, launch command: `./gradlew :desktop:run`
* Or choose **desktop** configuration in IDE and run it.

##
### Building native desktop distribution

```
./gradlew :desktop:packageReleaseDistributionForCurrentOS
# outputs are written to desktop/build/compose/binaries
```

##
### Author

<a href="https://github.com/dmitriy-chernysh" target="_blank">
  <img src="https://s.gravatar.com/avatar/72c649d298a8f0f088fd0850e19b9147?s=400" width="70" align="left">
</a>

**Dmitri Chernysh**

[![Instagram](https://img.shields.io/badge/-instagram-E4405F?logo=instagram&message=Tech+insights+on&label=Tech+insights+on&logoColor=white&style=for-the-badge)](https://www.instagram.com/mobiledevpro/)
[![Youtube](https://img.shields.io/badge/-youtube-red?logo=youtube&message=Youtube&label=Watch+on&style=for-the-badge)](https://www.youtube.com/@mobiledevpro?sub_confirmation=1&utm_source=github_main_profile)
[![Patreon](https://img.shields.io/badge/-patreon-f2a09b?logo=patreon&logoColor=white&label=Join+on&style=for-the-badge)](https://patreon.com/mobiledevpro)
[![Linkedin](https://img.shields.io/badge/-linkedin-0A66C2?logo=linkedin&logoColor=white&label=Follow+on&style=for-the-badge)](https://www.linkedin.com/in/dmitriychernysh/)
[![Upwork](https://img.shields.io/badge/-upwork-14a800?logo=Upwork&logoColor=white&label=Work+with+me+on&style=for-the-badge)](https://www.upwork.com/freelancers/dmitrich)

## License:

Copyright 2025 Dmitri Chernysh

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
