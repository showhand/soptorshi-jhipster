import { Moment } from 'moment';

export interface IAttendance {
    id?: number;
    employeeId?: string;
    attendanceDate?: Moment;
    inTime?: Moment;
    outTime?: Moment;
    attendanceExcelUploadId?: number;
}

export class Attendance implements IAttendance {
    constructor(
        public id?: number,
        public employeeId?: string,
        public attendanceDate?: Moment,
        public inTime?: Moment,
        public outTime?: Moment,
        public attendanceExcelUploadId?: number
    ) {}
}
