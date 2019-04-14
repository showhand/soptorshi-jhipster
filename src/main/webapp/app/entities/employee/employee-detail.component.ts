import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee/employee.service';

@Component({
    selector: 'jhi-employee-detail',
    templateUrl: './employee-detail.component.html'
})
export class EmployeeDetailComponent implements OnInit {
    @Input()
    employee: IEmployee;
    @Input()
    editable: boolean;
    @Output()
    closeEmployeeManagement: EventEmitter<any> = new EventEmitter();
    @Output()
    enableEdit: EventEmitter<any> = new EventEmitter();

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute, protected employeeService: EmployeeService) {}

    ngOnInit() {}

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        // window.history.back();
        this.closeEmployeeManagement.emit();
    }

    disableEdit() {
        this.editable = false;
    }

    edit() {
        this.enableEdit.emit();
    }
}
