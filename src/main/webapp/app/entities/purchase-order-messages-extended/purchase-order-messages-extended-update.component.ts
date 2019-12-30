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
import { PurchaseOrderMessagesService, PurchaseOrderMessagesUpdateComponent } from 'app/entities/purchase-order-messages';
import { AccountService } from 'app/core';

@Component({
    selector: 'jhi-purchase-order-messages-update',
    templateUrl: './purchase-order-messages-extended-update.component.html'
})
export class PurchaseOrderMessagesExtendedUpdateComponent extends PurchaseOrderMessagesUpdateComponent implements OnInit {
    loggedInEmployee: IEmployee;
    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected purchaseOrderMessagesService: PurchaseOrderMessagesService,
        protected employeeService: EmployeeService,
        protected purchaseOrderService: PurchaseOrderService,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        super(dataUtils, jhiAlertService, purchaseOrderMessagesService, employeeService, purchaseOrderService, activatedRoute);
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            const employeeId = account.login;
            this.employeeService
                .query({
                    'employeeId.equals': employeeId
                })
                .subscribe(response => {
                    this.loggedInEmployee = response.body[0];
                });
        });
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ purchaseOrderMessages }) => {
            this.purchaseOrderMessages = purchaseOrderMessages;
        });
    }

    save() {
        this.isSaving = true;
        this.purchaseOrderMessages.commenterId = this.loggedInEmployee.id;
        this.purchaseOrderMessages.commentedOn = moment();
        if (this.purchaseOrderMessages.id !== undefined) {
            this.subscribeToSaveResponse(this.purchaseOrderMessagesService.update(this.purchaseOrderMessages));
        } else {
            this.subscribeToSaveResponse(this.purchaseOrderMessagesService.create(this.purchaseOrderMessages));
        }
    }
}
