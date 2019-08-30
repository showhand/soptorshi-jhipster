import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Quotation } from 'app/shared/model/quotation.model';
import { QuotationService } from './quotation.service';
import { QuotationComponent } from './quotation.component';
import { QuotationDetailComponent } from './quotation-detail.component';
import { QuotationUpdateComponent } from './quotation-update.component';
import { QuotationDeletePopupComponent } from './quotation-delete-dialog.component';
import { IQuotation } from 'app/shared/model/quotation.model';

@Injectable({ providedIn: 'root' })
export class QuotationResolve implements Resolve<IQuotation> {
    constructor(public service: QuotationService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IQuotation> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Quotation>) => response.ok),
                map((quotation: HttpResponse<Quotation>) => quotation.body)
            );
        }
        return of(new Quotation());
    }
}

export const quotationRoute: Routes = [
    {
        path: '',
        component: QuotationComponent,
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
        path: ':id/view',
        component: QuotationDetailComponent,
        resolve: {
            quotation: QuotationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Quotations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: QuotationUpdateComponent,
        resolve: {
            quotation: QuotationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Quotations'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: QuotationUpdateComponent,
        resolve: {
            quotation: QuotationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Quotations'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const quotationPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: QuotationDeletePopupComponent,
        resolve: {
            quotation: QuotationResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Quotations'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
