import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAttendance } from 'app/shared/model/attendance.model';
import { AttendanceService } from './attendance.service';
import { IAttendanceExcelUpload } from 'app/shared/model/attendance-excel-upload.model';
import { AttendanceExcelUploadService } from 'app/entities/attendance-excel-upload';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';

@Component({
    selector: 'jhi-attendance-update',
    templateUrl: './attendance-update.component.html'
})
export class AttendanceUpdateComponent implements OnInit {
    attendance: IAttendance;
    isSaving: boolean;

    attendanceexceluploads: IAttendanceExcelUpload[];

    employees: IEmployee[];
    attendanceDateDp: any;
    inTime: string;
    outTime: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected attendanceService: AttendanceService,
        protected attendanceExcelUploadService: AttendanceExcelUploadService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ attendance }) => {
            this.attendance = attendance;
            this.inTime = this.attendance.inTime != null ? this.attendance.inTime.format(DATE_TIME_FORMAT) : null;
            this.outTime = this.attendance.outTime != null ? this.attendance.outTime.format(DATE_TIME_FORMAT) : null;
        });
        this.attendanceExcelUploadService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAttendanceExcelUpload[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAttendanceExcelUpload[]>) => response.body)
            )
            .subscribe(
                (res: IAttendanceExcelUpload[]) => (this.attendanceexceluploads = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.employeeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEmployee[]>) => response.body)
            )
            .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.attendance.inTime = this.inTime != null ? moment(this.inTime, DATE_TIME_FORMAT) : null;
        this.attendance.outTime = this.outTime != null ? moment(this.outTime, DATE_TIME_FORMAT) : null;
        if (this.attendance.id !== undefined) {
            this.subscribeToSaveResponse(this.attendanceService.update(this.attendance));
        } else {
            this.subscribeToSaveResponse(this.attendanceService.create(this.attendance));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAttendance>>) {
        result.subscribe((res: HttpResponse<IAttendance>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAttendanceExcelUploadById(index: number, item: IAttendanceExcelUpload) {
        return item.id;
    }

    trackEmployeeById(index: number, item: IEmployee) {
        return item.id;
    }
}
