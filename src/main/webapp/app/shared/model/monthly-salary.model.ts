import { Moment } from 'moment';
import { ISalaryMessages } from 'app/shared/model/salary-messages.model';

export const enum MonthType {
    JANUARY = 'JANUARY',
    FEBRUARY = 'FEBRUARY',
    MARCH = 'MARCH',
    APRIL = 'APRIL',
    MAY = 'MAY',
    JUNE = 'JUNE',
    JULY = 'JULY',
    AUGUST = 'AUGUST',
    SEPTEMBER = 'SEPTEMBER',
    OCTOBER = 'OCTOBER',
    NOVEMBER = 'NOVEMBER',
    DECEMBER = 'DECEMBER'
}

export const enum MonthlySalaryStatus {
    APPROVED_BY_MANAGER = 'APPROVED_BY_MANAGER',
    APPROVED_BY_ACCOUNTS = 'APPROVED_BY_ACCOUNTS',
    MODIFICATION_REQUEST_BY_ACCOUNTS = 'MODIFICATION_REQUEST_BY_ACCOUNTS',
    APPROVED_BY_CFO = 'APPROVED_BY_CFO',
    MODIFICATION_REQUEST_BY_CFO = 'MODIFICATION_REQUEST_BY_CFO',
    APPROVED_BY_MD = 'APPROVED_BY_MD',
    MODIFICATION_REQUEST_BY_MD = 'MODIFICATION_REQUEST_BY_MD'
}

export interface IMonthlySalary {
    id?: number;
    year?: number;
    month?: MonthType;
    basic?: number;
    gross?: number;
    houseRent?: number;
    medicalAllowance?: number;
    overTimeAllowance?: number;
    foodAllowance?: number;
    arrearAllowance?: number;
    driverAllowance?: number;
    fuelLubAllowance?: number;
    mobileAllowance?: number;
    travelAllowance?: number;
    otherAllowance?: number;
    festivalAllowance?: number;
    absent?: number;
    fine?: number;
    advanceHO?: number;
    advanceFactory?: number;
    providentFund?: number;
    tax?: number;
    loanAmount?: number;
    billPayable?: number;
    billReceivable?: number;
    payable?: number;
    approved?: boolean;
    onHold?: boolean;
    status?: MonthlySalaryStatus;
    modifiedBy?: string;
    modifiedOn?: Moment;
    voucherGenerated?: boolean;
    comments?: ISalaryMessages[];
    employeeFullName?: string;
    employeeId?: number;
}

export class MonthlySalary implements IMonthlySalary {
    constructor(
        public id?: number,
        public year?: number,
        public month?: MonthType,
        public basic?: number,
        public gross?: number,
        public houseRent?: number,
        public medicalAllowance?: number,
        public overTimeAllowance?: number,
        public foodAllowance?: number,
        public arrearAllowance?: number,
        public driverAllowance?: number,
        public fuelLubAllowance?: number,
        public mobileAllowance?: number,
        public travelAllowance?: number,
        public otherAllowance?: number,
        public festivalAllowance?: number,
        public absent?: number,
        public fine?: number,
        public advanceHO?: number,
        public advanceFactory?: number,
        public providentFund?: number,
        public tax?: number,
        public loanAmount?: number,
        public billPayable?: number,
        public billReceivable?: number,
        public payable?: number,
        public approved?: boolean,
        public onHold?: boolean,
        public status?: MonthlySalaryStatus,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public voucherGenerated?: boolean,
        public comments?: ISalaryMessages[],
        public employeeFullName?: string,
        public employeeId?: number
    ) {
        this.approved = this.approved || false;
        this.onHold = this.onHold || false;
        this.voucherGenerated = this.voucherGenerated || false;
    }
}
