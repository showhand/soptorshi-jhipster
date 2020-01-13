import { Moment } from 'moment';

export interface IAttendance {
    id?: number;
    attendanceDate?: Moment;
    inTime?: Moment;
    outTime?: Moment;
    duration?: string;
    attendanceExcelUploadId?: number;
    employeeFullName?: string;
    employeeId?: number;
}

export class Attendance implements IAttendance {
    constructor(
        public id?: number,
        public attendanceDate?: Moment,
        public inTime?: Moment,
        public outTime?: Moment,
        public duration?: string,
        public attendanceExcelUploadId?: number,
        public employeeFullName?: string,
        public employeeId?: number
    ) {}
}
