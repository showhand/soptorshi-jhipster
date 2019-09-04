import { Moment } from 'moment';

export interface IAttendance {
    id?: number;
    employeeId?: string;
    attendanceDate?: Moment;
    inTime?: Moment;
    outTime?: Moment;
    diff?: string;
    attendanceExcelUploadId?: number;
}

export class Attendance implements IAttendance {
    constructor(
        public id?: number,
        public employeeId?: string,
        public attendanceDate?: Moment,
        public inTime?: Moment,
        public outTime?: Moment,
        public diff?: string,
        public attendanceExcelUploadId?: number
    ) {}
}
