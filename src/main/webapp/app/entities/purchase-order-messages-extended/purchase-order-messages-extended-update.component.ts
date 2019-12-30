import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPurchaseOrderMessages } from 'app/shared/model/purchase-order-messages.model';
import { PurchaseOrderMessagesExtendedService } from './purchase-order-messages-extended.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { IPurchaseOrder } from 'app/shared/model/purchase-order.model';
import { PurchaseOrderService } from 'app/entities/purchase-order';
import { PurchaseOrderMessagesUpdateComponent } from 'app/entities/purchase-order-messages';

@Component({
    selector: 'jhi-purchase-order-messages-update',
    templateUrl: './purchase-order-messages-extended-update.component.html'
})
export class PurchaseOrderMessagesExtendedUpdateComponent extends PurchaseOrderMessagesUpdateComponent implements OnInit {
    constructor(
        dataUtils: JhiDataUtils,
        jhiAlertService: JhiAlertService,
        purchaseOrderMessagesService: PurchaseOrderMessagesExtendedService,
        employeeService: EmployeeService,
        purchaseOrderService: PurchaseOrderService,
        activatedRoute: ActivatedRoute
    ) {
        super(dataUtils, jhiAlertService, purchaseOrderMessagesService, employeeService, purchaseOrderService, activatedRoute);
    }
}
