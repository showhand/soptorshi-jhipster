import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Quotation } from 'app/shared/model/quotation.model';
import { IQuotation } from 'app/shared/model/quotation.model';
import { QuotationDeletePopupComponent, QuotationDetailComponent, QuotationResolve, QuotationService } from 'app/entities/quotation';
import { QuotationExtendedComponent } from 'app/entities/quotation-extended/quotation-extended.component';
import { QuotationExtendedDetailComponent } from 'app/entities/quotation-extended/quotation-extended-detail.component';
import { QuotationExtendedUpdateComponent } from 'app/entities/quotation-extended/quotation-extended-update.component';

@Injectable({ providedIn: 'root' })
export class QuotationExtendedResolve extends QuotationResolve {
    constructor(public service: QuotationService) {
        super(service);
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IQuotation> {
        const id = route.params['id'] ? route.params['id'] : null;
        const requisitionId = route.params['requisitionId'] ? route.params['requisitionId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Quotation>) => response.ok),
                map((quotation: HttpResponse<Quotation>) => quotation.body)
            );
        } else if (requisitionId) {
            const quotation = new Quotation();
            quotation.requisitionId = requisitionId;
            return of(quotation);
        }
        return of(new Quotation());
    }
}

export const quotationExtendedRoute: Routes = [
    {
        path: '',
        component: QuotationExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Quotations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':requisitionId/requisition',
        component: QuotationExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams,
            quotation: QuotationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Quotations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: QuotationExtendedDetailComponent,
        resolve: {
            quotation: QuotationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Quotations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':requisitionId/new',
        component: QuotationExtendedUpdateComponent,
        resolve: {
            quotation: QuotationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Quotations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: QuotationExtendedUpdateComponent,
        resolve: {
            quotation: QuotationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Quotations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: QuotationExtendedUpdateComponent,
        resolve: {
            quotation: QuotationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Quotations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const quotationExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: QuotationDeletePopupComponent,
        resolve: {
            quotation: QuotationExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Quotations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
