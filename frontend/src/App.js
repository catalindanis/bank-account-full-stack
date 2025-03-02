import './App.css';
import {useState} from "react";
import AddTransactionComponent from "./AddTransactionComponent";
import TransactionsHistoryComponent from "./TransactionsHistoryComponent";
import Undo from "./Undo";

function App() {
    const [transactions, setTransactions] = useState([]);

    return (
        <div className="App" style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
            <div style={{display: 'flex', flexDirection: 'row', alignItems: 'center'}}>
                <img src="./logo.png" style={{height: 25, marginRight: 10}}/>
                <h1>Bank account</h1>
            </div>
            <AddTransactionComponent transactions={transactions} setTransactions={setTransactions}></AddTransactionComponent>
            <TransactionsHistoryComponent transactions={transactions} setTransactions={setTransactions}></TransactionsHistoryComponent>
        </div>
    );
}


export default App;
