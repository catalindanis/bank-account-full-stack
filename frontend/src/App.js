import './App.css';
import {useState} from "react";

function App() {

    const [amount, setAmount] = useState(null);
    const [date, setDate] = useState(null);
    const [type, setType] = useState(null);
    const [firstRadio, setFirstRadio] = useState(null);

    return (
        <div className="App" style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
            <div style={{display: 'flex', flexDirection: 'row', alignItems: 'center'}}>
                <img src="./logo.png" style={{height: 25, marginRight: 10}}/>
                <h1>Bank account</h1>
            </div>
                <div style={{display: 'flex', flexDirection: 'row', alignItems: 'center'}}>
                    <div style={{marginRight: 20}}>
                        <h4 style={{marginBottom: 0}}>Amount</h4>
                        <input type={"number"} onChange={e => setAmount(e.target.value)}/>
                    </div>
                    <div style={{marginRight: 20}}>
                        <h4 style={{marginBottom: 0}}>Date</h4>
                        <input type={"date"} onChange={e => setDate(e.target.value)}/>
                    </div>
                    <div style={{marginRight: 20}}>
                        <h4 style={{marginBottom: 0}}>Type</h4>
                        <label style={{marginRight: 10}}>
                            <input type={"radio"} onChange={() => setType("enter")}
                                   checked={type == "enter" ? true : false}/>
                            Enter
                        </label>
                        <label>
                            <input type={"radio"} onChange={() => setType("exit")}
                                   checked={type == "exit" ? true : false}/>
                            Exit
                        </label>
                    </div>
                    <div>
                        <button style={{marginTop: 45}} onClick={() => {
                            
                        }}>
                            Add transaction
                        </button>
                    </div>
                </div>
            </div>
    );
}

export default App;
