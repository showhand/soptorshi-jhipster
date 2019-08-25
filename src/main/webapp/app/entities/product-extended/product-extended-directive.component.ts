import { ProductExtendedComponent } from 'app/entities/product-extended/product-extended.component';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IProduct, Product } from 'app/shared/model/product.model';
import { ProductComponent, ProductService } from 'app/entities/product';
import { JhiAlertService, JhiDataUtils, JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { AccountService } from 'app/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ITEMS_PER_PAGE } from 'app/shared';
import { ProductExtendedService } from 'app/entities/product-extended/product-extended.service';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ProductExtendedUpdateComponent } from 'app/entities/product-extended/product-extended-update.component';

@Component({
    selector: 'jhi-product-extended-directive',
    templateUrl: './product-extended-directive.component.html'
})
export class ProductExtendedDirectiveComponent extends ProductComponent implements OnInit, OnDestroy {
    @Input()
    productCategoryId: number;

    modalRef: NgbModalRef;

    constructor(
        protected productService: ProductService,
        protected parseLinks: JhiParseLinks,
        protected jhiAlertService: JhiAlertService,
        protected accountService: AccountService,
        protected activatedRoute: ActivatedRoute,
        protected dataUtils: JhiDataUtils,
        protected router: Router,
        protected eventManager: JhiEventManager,
        protected productExtendedService: ProductExtendedService,
        protected modalService: NgbModal
    ) {
        super(productService, parseLinks, jhiAlertService, accountService, activatedRoute, dataUtils, router, eventManager);

        this.productExtendedService.page = this.productExtendedService.page == null ? 1 : this.productExtendedService.page;
        this.productExtendedService.previousPage =
            this.productExtendedService.predicate == null ? 0 : this.productExtendedService.previousPage;
        this.productExtendedService.reverse = this.productExtendedService.reverse == null ? 'desc' : this.productExtendedService.reverse;
        this.productExtendedService.predicate = 'id';
    }

    loadAll() {
        this.productService
            .query({
                'productCategoryId.equals': this.productCategoryId,
                page: this.productExtendedService.page,
                size: this.itemsPerPage,
                sort: 'desc'
            })
            .subscribe(
                (res: HttpResponse<IProduct[]>) => this.paginateProducts(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInProducts();
    }

    add() {
        this.modalRef = this.modalService.open(ProductExtendedUpdateComponent, { size: 'lg', backdrop: 'static' });
        this.modalRef.componentInstance.product = new Product();
        this.modalRef.componentInstance.modalRef = this.modalRef;
        this.modalRef.result.then(() => {
            this.loadAll();
        });
    }
}
