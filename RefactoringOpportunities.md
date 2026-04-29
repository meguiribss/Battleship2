| Local | Code Smell | Refactoring | Aluno  |
|------|-----------|------------|--------|
| Game::printBoard | Long Method | Extract Method | 122978 |
| Game::printBoard | Duplicated Code (loops de impressão) | Extract Method | 122978 |
| Game::jsonShots | Duplicated Code (ObjectMapper repetido) | Extract Method | 122978 |
| Game::randomEnemyFire | Long Method | Extract Method | 122978 |
| Game::randomEnemyFire | Complex Logic (if/else grande) | Decompose Conditional | 122978 |
| Move::processEnemyFire | Long Method | Extract Method | 122998 |
| Move::processEnemyFire | Primitive Obsession (maps) | Introduce Class | 122998 |
| Move::processEnemyFire | Complex Condition | Extract Variable | 122998 |
| Ship::buildShip | Switch Statement | Replace with Polymorphism | 122998 |
| Ship::constructor | Duplicate assignments | Remove Redundant Code | 122998 |
| Ship::getTopMostPos | Duplicated Code | Extract Method | 124133 |
| Ship::getBottomMostPos | Duplicated Code | Extract Method | 124133 |
| Ship::getLeftMostPos | Duplicated Code | Extract Method | 124133 |
| Ship::getRightMostPos | Duplicated Code | Duplicated Code | 124133 |
| Fleet::addShip | Complex Condition | Extract Method | 124133 |
| Fleet::createRandom | Magic Strings | Replace with Constants | 124133 |
| Scoreboard::save | Poor Exception Handling | Replace with Logging | 124133 |
| Galleon::constructor | Switch Statement | Replace with Polymorphism | 124133 |