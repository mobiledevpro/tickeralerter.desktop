# Ticker Alerter Desktop App

* Build a native package ```./gradlew package```

### Roadmap

* [ ] Connect to Binance API WebSocket
* [x] Getting all available tickers from API
* [ ] Subscribe to all tickers to getting update from WebSocket
* [ ] On selecting ticker in UI get historical data and save it into local database
* [ ] Draw chart by historical data
* [ ] Think about strategy RSI + MACD ?
* [ ] Calculate RSI and display for selected ticker
* [ ] Calculate MACD
* [ ] Getting all opened positions
* [ ] Display current PL
* [ ] Ability to run backtest for selected ticker + time frame + period

There are multiple modules:

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
./gradlew :desktop:packageDistributionForCurrentOS
# outputs are written to desktop/build/compose/binaries
```