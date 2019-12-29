import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IRequisitionMessages } from 'app/shared/model/requisition-messages.model';
import { RequisitionMessagesExtendedService } from './requisition-messages-extended.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionService } from 'app/entities/requisition';
import { RequisitionMessagesUpdateComponent } from 'app/entities/requisition-messages';

@Component({
    selector: 'jhi-requisition-messages-update',
    templateUrl: './requisition-messages-extended-update.component.html'
})
export class RequisitionMessagesExtendedUpdateComponent extends RequisitionMessagesUpdateComponent implements OnInit {
    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected requisitionMessagesService: RequisitionMessagesExtendedService,
        protected employeeService: EmployeeService,
        protected requisitionService: RequisitionService,
        protected activatedRoute: ActivatedRoute
    ) {
        super(dataUtils, jhiAlertService, requisitionMessagesService, employeeService, requisitionService, activatedRoute);
    }
}
