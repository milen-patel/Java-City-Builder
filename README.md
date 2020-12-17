
# Welcome to City Builder

The goal of this program was to create a city-building game using Java Swing and other programming concepts learned in UNC's COMP401 class. The game incorporates design patterns including Factory, Model-View, and Observer/Observable.


## How to Run

Once you have cloned the repository, navigate to src/main/ and execute Runner.java

## How to Play

When you execute Runner.java, an instance of the game will automatically be created. On the left side of the screen is a 25x25 grid that represents a top-down view of your city. By clicking on any spot of the grid, you have the ability to construct or demolish buildings with a user prompt. 

On the right hand side of the screen, there are a series of labels and then a series of buttons. The first label represents your balance. You will spend money to construct buildings and you will earn money from various buildings that you construct. The game will prohibit you from having a balance less than zero dollars. The second label represents your daily income, as the summation of the daily incomes from each of the buildings in your city. The third label represents your population, as a summation of the number of residents that each of your buildings can house. The fourth label represents your day. 

City Builder is a game that operates on a day-by-day basis. You may construct and demolish buildings at your desire between days. Incrementing the day will update your balance by the daily income.

The fourth label represents your overall happiness, on a scale of negative infinity to infinity. The mechanics for calculating balance will be discussed later. The fifth label represents your unemployment rate, the implications of high and low unemployment rates will also be discussed later.

The next day button will skip your simulation to the next day and the play/pause button will automatically update the day with 100 millisecond pauses between each day.

Below the sequence of labels and buttons is a log of all the events that have happened in your simulation including the construction of new buildings and the passing of new days. Clicking the 'prices' button will show the current prices to construct each building in your log. The log is never cleared and can be viewed entirely from the start of the game.

The game is free to be played endlessly and has no winning or losing condition.

## Types of Buildings

City Builder allows you to construct various buildings which are unique for their cost, number of residents, and number of jobs available. You also have the ability to destroy any building with the exception of roads, grass, and water; however, the cost of destruction increases as you demolish more and more buildings.

As you construct more instances of each building, the price will begin to increase. 

|                |  Cost                         |Daily Income                 |
|----------------|-------------------------------|-----------------------------|
|Apartment		   | $10,000.00.                   |$0.00 - $1,000.00            |
|Factory         | $100,000                      |$35,000.00 - $60,000.00      |
|House           | $500.00                       |$0.00 - $100.00              |
|Retail          | $50,000.00                    |$10,250.00 - $10,350.00      |
|Park            | $500.00                       |$0.00 - $100.00              |
|Road            | $500.00                       |$0.00                        |



There are restrictions as to where you can construct various buildings.  

**Apartments:** must be constructed on a position that directly touches a road (not on a corner)  
**Factory:** must be constructed on a position that directly touches both a road and water  
**House:** must be constructed on a position that directly touches a road  
**Retail:** must be constructed on a position that directly touches a road  
**Park:** must be constructed on a position that directly touches water  
**Road:** must be constructed on a position that directly touches another piece of road  

