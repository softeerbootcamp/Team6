import { _$ } from "../../common/utils";

String.prototype.foreach = Array.prototype.forEach;
const OIL_PRICE_UPPER_BOUND = 150_000;


const parseOilPriceIntoKorean = ($input, $effectImage) => {
    let inputValue = $input.value;

    if(inputValue[0] === "-") {
        $input.value = "";
        return "음수는 불가능합니다.";
    }

    let inputToNumber = parseInt(inputValue);
    if(isNaN(inputToNumber)) return "숫자를 입력해주세요.";

    inputValue = String(inputToNumber);  // 앞에 0이 나오는 거 방지
    $input.value = inputValue;

    animateOilImage($effectImage, inputToNumber);

    let parsedString = "";

    for(let index=inputValue.length-1, j=0;index>=0;index--, j += 1) {
        if(j && j % 3 == 0) {
            parsedString += ",";
        }
        parsedString += inputValue[index];
    }

    parsedString = parsedString.split("").reverse().join("");

    return parsedString + " 원";
}

const animateOilImage = ($oilImage, inputValue) => {    
    let posY = 0;
    if(inputValue >= OIL_PRICE_UPPER_BOUND) {
        posY = 0;
    }
    else if(inputValue >= 120_000) {
        posY = 150 * 1;
    }
    else if(inputValue >= 100_000) {
        posY = 150 * 2;
    }
    else if(inputValue >= 50_000) {
        posY = 150 * 3;
    }
    else if(inputValue >= 10_000) {
        posY = 150 * 4;
    }
    else if (inputValue >= 5000) {
        posY = 150 * 5;
    }
    else if (inputValue >= 3000) {
        posY = 150 * 6;
    }
    else if (inputValue >= 1000) {
        posY = 150 * 7;
    }
    else if (inputValue >= 100) {
        posY = 150 * 8;
    }
    else if (inputValue >= 10) {
        posY = 150 * 9;
    }
    else {
        posY = 1500;
    }

    $oilImage.setAttribute("y", posY);
    $oilImage.setAttribute("fill", "#14BD7E");
}

const validateGasName = ($gasText) => {
   if($gasText.innerHTML === "기름 종류") return false;
   return true;
}

const validateGasPrice = ($gasPrice) => {
    return !($gasPrice.value === "" || $gasPrice.value === "0");
}

const validateStationName = ($gasStationText) => {
    if(!$gasStationText.disabled) {
        return false;
    }
    return !($gasStationText.value === "");
}

const validateOilInput = ($oilText, $oilPriceText, $gasStationText) => 
    validateGasName($oilText) && validateGasPrice($oilPriceText) && validateStationName($gasStationText);

const gasNameMapper = (gasName) => {
    switch(gasName) {
        case "휘발유":
            return "GASOLINE";

        case "경유":
            return "DIESEL";

        case "LPG":
            return "LPG";
    }
}

export { 
    parseOilPriceIntoKorean, animateOilImage,
    validateGasName, validateGasPrice, validateStationName, validateOilInput,
    gasNameMapper
}