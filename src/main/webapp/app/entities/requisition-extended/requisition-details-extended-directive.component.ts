import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { RequisitionDetailsExtendedComponent, RequisitionDetailsExtendedUpdateComponent } from 'app/entities/requisition-details-extended';
import { IRequisition } from 'app/shared/model/requisition.model';
import { RequisitionDetailsService } from 'app/entities/requisition-details';
import { JhiAlertService, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RequisitionService } from 'app/entities/requisition';
import { RequisitionDetails } from 'app/shared/model/requisition-details.model';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { RequisitionExtendedService } from 'app/entities/requisition-extended/requisition-extended.service';

@Component({
    selector: 'jhi-requisition-details-extended-directive',
    templateUrl: './requisition-details-extended-directive.component.html'
})
export class RequisitionDetailsExtendedDirectiveComponent extends RequisitionDetailsExtendedComponent implements OnInit, OnDestroy {
    @Input()
    requisition: IRequisition;

    constructor(
        protected requisitionDetailsService: RequisitionDetailsService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected router: Router,
        protected eventManager: JhiEventManager,
        protected requisitionService: RequisitionExtendedService,
        public modalService: NgbModal
    ) {
        super(
            requisitionDetailsService,
            parseLinks,
            jhiAlertService,
            accountService,
            activatedRoute,
            router,
            eventManager,
            requisitionService
        );

        this.itemsPerPage = 100;
        this.page = 1;
        this.previousPage = 0;
        this.reverse = 'asc';
        this.predicate = 'id';
    }

    ngOnInit() {
        this.requisitionDetail = new RequisitionDetails();
        this.requisitionDetail.requisitionId = this.requisition.id;
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRequisitionDetails();
    }

    transition() {
        this.page = this.requisitionService.requisitionDetailsPage;
        this.previousPage = this.requisitionService.requisitionDetailsPreviousPage;
        this.reverse = this.requisitionService.requisitionDetailsReverse;
        this.predicate = this.requisitionService.requisitionDetailsPredicate;
        this.loadAll();
    }

    add() {}

    delete(id: number): void {
        this.requisitionDetailsService.delete(id).subscribe(
            res => {
                this.jhiAlertService.success('Delete is successful');
            },
            error => {
                this.jhiAlertService.error(error);
            },
            () => {
                this.loadAll();
            }
        );
    }
}
