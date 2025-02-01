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

### How to run the project on you local machine:

* You have to be registered on Binance and have an API key
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

### Modules:

- `:common:utils` - just some useful helpers
- `:common:database` - SQLDelight database definition
- `:desktop` - Desktop application
- `:feature:main` - the main screen
- `:feature:chart`
- `:feature:tickerlist`
- `:feature:tradinglog`

### Running desktop application

* To run, launch command: `./gradlew :desktop:run`
* Or choose **desktop** configuration in IDE and run it.

#### Building native desktop distribution

```
./gradlew :desktop:packageReleaseDistributionForCurrentOS
# outputs are written to desktop/build/compose/binaries
```
