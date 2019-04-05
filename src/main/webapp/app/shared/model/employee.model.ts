import { Moment } from 'moment';

export const enum MaritalStatus {
    MARRIED = 'MARRIED',
    UNMARRIED = 'UNMARRIED',
    SEPARATED = 'SEPARATED'
}

export const enum Gender {
    MALE = 'MALE',
    FEMALE = 'FEMALE',
    OTHERS = 'OTHERS'
}

export const enum Religion {
    ISLAM = 'ISLAM',
    HINDU = 'HINDU',
    BUDDHIST = 'BUDDHIST',
    CHRISTIANS = 'CHRISTIANS',
    OTHERS = 'OTHERS'
}

export const enum EmployeeStatus {
    ACTIVE = 'ACTIVE',
    TERMINATED = 'TERMINATED'
}

export const enum EmploymentType {
    PERMANENT = 'PERMANENT',
    TEMPORARY = 'TEMPORARY',
    ADHOC = 'ADHOC',
    PART_TIME = 'PART_TIME'
}

export interface IEmployee {
    id?: number;
    employeeId?: string;
    fullName?: string;
    fathersName?: string;
    mothersName?: string;
    birthDate?: Moment;
    age?: number;
    maritalStatus?: MaritalStatus;
    gender?: Gender;
    religion?: Religion;
    permanentAddress?: string;
    presentAddress?: string;
    nId?: string;
    tin?: string;
    contactNumber?: string;
    email?: string;
    bloodGroup?: string;
    emergencyContact?: string;
    employeeStatus?: EmployeeStatus;
    employmentType?: EmploymentType;
    terminationDate?: Moment;
    reasonOfTermination?: string;
    userAccount?: boolean;
    departmentId?: number;
    designationId?: number;
}

export class Employee implements IEmployee {
    constructor(
        public id?: number,
        public employeeId?: string,
        public fullName?: string,
        public fathersName?: string,
        public mothersName?: string,
        public birthDate?: Moment,
        public age?: number,
        public maritalStatus?: MaritalStatus,
        public gender?: Gender,
        public religion?: Religion,
        public permanentAddress?: string,
        public presentAddress?: string,
        public nId?: string,
        public tin?: string,
        public contactNumber?: string,
        public email?: string,
        public bloodGroup?: string,
        public emergencyContact?: string,
        public employeeStatus?: EmployeeStatus,
        public employmentType?: EmploymentType,
        public terminationDate?: Moment,
        public reasonOfTermination?: string,
        public userAccount?: boolean,
        public departmentId?: number,
        public designationId?: number
    ) {
        this.userAccount = this.userAccount || false;
    }
}
