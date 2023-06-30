# Ticker Alerter Desktop App

* Build a native package ```./gradlew package```

### Roadmap

* [x] Connect to Binance API WebSocket
* [x] Getting all available tickers from API
* [x] Subscribe to tickers to getting update from WebSocket
* [x] On selecting ticker in UI get historical data and save it into local database
* [x] Draw chart by historical data
* [ ] Simple alerts by price change (crossing, crossing up, crossing down)
* [ ] EMA Ribbon strategy for Alerts
* [ ] Real-time Chart by selected timeframe
* [ ] Timeframe picker for Chart (5m, 15m, 30m, 1h, 4h, 1d, 1m)
* [ ] Backtesting for selected period (date from - date to)
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