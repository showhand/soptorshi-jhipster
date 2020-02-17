import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService } from 'ng-jhipster';
import { AttendanceExcelUploadService } from 'app/entities/attendance-excel-upload';
import { AttendanceService, AttendanceUpdateComponent } from 'app/entities/attendance';
import { EmployeeService } from 'app/entities/employee';

@Component({
    selector: 'jhi-attendance-update-extended',
    templateUrl: './attendance-update-extended.component.html'
})
export class AttendanceUpdateExtendedComponent extends AttendanceUpdateComponent {
    constructor(
        protected jhiAlertService: JhiAlertService,
        protected attendanceService: AttendanceService,
        protected employeeService: EmployeeService,
        protected attendanceExcelUploadService: AttendanceExcelUploadService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(jhiAlertService, attendanceService, employeeService, attendanceExcelUploadService, activatedRoute);
    }
}
