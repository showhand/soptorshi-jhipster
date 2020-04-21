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
    IN_PROBATION = 'IN_PROBATION',
    ACTIVE = 'ACTIVE',
    RESIGNED = 'RESIGNED',
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
    employeeLongId?: number;
    employeeId?: string;
    fullName?: string;
    fathersName?: string;
    mothersName?: string;
    birthDate?: Moment;
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
    joiningDate?: Moment;
    manager?: number;
    employeeStatus?: EmployeeStatus;
    employmentType?: EmploymentType;
    terminationDate?: Moment;
    reasonOfTermination?: string;
    userAccount?: boolean;
    photoContentType?: string;
    photo?: any;
    hourlySalary?: number;
    bankAccountNo?: string;
    bankName?: string;
    departmentName?: string;
    departmentId?: number;
    officeName?: string;
    officeId?: number;
    designationName?: string;
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
        public joiningDate?: Moment,
        public manager?: number,
        public employeeStatus?: EmployeeStatus,
        public employmentType?: EmploymentType,
        public terminationDate?: Moment,
        public reasonOfTermination?: string,
        public userAccount?: boolean,
        public photoContentType?: string,
        public photo?: any,
        public hourlySalary?: number,
        public bankAccountNo?: string,
        public bankName?: string,
        public departmentName?: string,
        public departmentId?: number,
        public officeName?: string,
        public officeId?: number,
        public designationName?: string,
        public designationId?: number
    ) {
        this.userAccount = this.userAccount || false;
    }
}
