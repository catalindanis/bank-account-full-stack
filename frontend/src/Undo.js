import axios from "axios";
import {reloadTransactions} from "./TransactionsHistoryComponent";

function Undo({transactions, setTransactions}){
    return (
        <button onClick={() => {
            undoLastOperation(transactions, setTransactions);
        }}>
            Undo
        </button>
    );
}

async function undoLastOperation(transactions, setTransactions){
    try{
        const response = await axios.put('http://localhost:8080/undo')
        if(response.status === 200) {
            reloadTransactions(transactions, setTransactions);
            return "Undo successfully executed"
        }
        return response.data;
    }
    catch (error) {
        return "An unknown error occurred. Check the log to see more details."
    }
}

export default Undo;