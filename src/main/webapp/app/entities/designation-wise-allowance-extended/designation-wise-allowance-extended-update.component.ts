import { DesignationWiseAllowanceService, DesignationWiseAllowanceUpdateComponent } from 'app/entities/designation-wise-allowance';
import { Component, OnInit } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { DesignationService } from 'app/entities/designation';
import { ActivatedRoute, Router } from '@angular/router';
import { AccountService } from 'app/core';
import { filter, map } from 'rxjs/operators';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IDesignation } from 'app/shared/model/designation.model';

@Component({
    selector: 'jhi-designation-wise-allowance-extended-update',
    templateUrl: './designation-wise-allowance-extended-update.component.html'
})
export class DesignationWiseAllowanceExtendedUpdateComponent extends DesignationWiseAllowanceUpdateComponent implements OnInit {
    currentAccount: Account;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected designationWiseAllowanceService: DesignationWiseAllowanceService,
        protected designationService: DesignationService,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService,
        protected router: Router
    ) {
        super(jhiAlertService, designationWiseAllowanceService, designationService, activatedRoute, accountService, router);
    }

    ngOnInit() {
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });

        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ designationWiseAllowance }) => {
            this.designationWiseAllowance = designationWiseAllowance;
            this.accountService.identity().then(account => {
                this.designationWiseAllowance.modifiedBy = account.toString();
            });
        });
        this.designationService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDesignation[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDesignation[]>) => response.body)
            )
            .subscribe((res: IDesignation[]) => (this.designations = res), (res: HttpErrorResponse) => this.onError(res.message));
    }
}
