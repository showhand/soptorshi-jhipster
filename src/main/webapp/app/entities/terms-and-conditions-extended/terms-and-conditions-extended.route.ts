import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TermsAndConditions } from 'app/shared/model/terms-and-conditions.model';
import { TermsAndConditionsExtendedComponent } from './terms-and-conditions-extended.component';
import { TermsAndConditionsExtendedDetailComponent } from './terms-and-conditions-extended-detail.component';
import { TermsAndConditionsExtendedUpdateComponent } from './terms-and-conditions-extended-update.component';
import { ITermsAndConditions } from 'app/shared/model/terms-and-conditions.model';
import { TermsAndConditionsDeletePopupComponent, TermsAndConditionsService } from 'app/entities/terms-and-conditions';

@Injectable({ providedIn: 'root' })
export class TermsAndConditionsExtendedResolve implements Resolve<ITermsAndConditions> {
    constructor(private service: TermsAndConditionsService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITermsAndConditions> {
        const id = route.params['id'] ? route.params['id'] : null;
        const purchaseOrderId = route.params['purchaseOrderId'] ? route.params['purchaseOrderId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TermsAndConditions>) => response.ok),
                map((termsAndConditions: HttpResponse<TermsAndConditions>) => termsAndConditions.body)
            );
        } else if (purchaseOrderId) {
            const termsAndConditions = new TermsAndConditions();
            termsAndConditions.purchaseOrderId = purchaseOrderId;
            return of(termsAndConditions);
        }
        return of(new TermsAndConditions());
    }
}

export const termsAndConditionsExtendedRoute: Routes = [
    {
        path: '',
        component: TermsAndConditionsExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'TermsAndConditions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':purchaseOrderId/purchaseOrder',
        component: TermsAndConditionsExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams,
            termsAndConditions: TermsAndConditionsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'TermsAndConditions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TermsAndConditionsExtendedDetailComponent,
        resolve: {
            termsAndConditions: TermsAndConditionsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TermsAndConditions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':purchaseOrderId/new',
        component: TermsAndConditionsExtendedUpdateComponent,
        resolve: {
            termsAndConditions: TermsAndConditionsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TermsAndConditions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TermsAndConditionsExtendedUpdateComponent,
        resolve: {
            termsAndConditions: TermsAndConditionsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TermsAndConditions'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TermsAndConditionsExtendedUpdateComponent,
        resolve: {
            termsAndConditions: TermsAndConditionsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TermsAndConditions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const termsAndConditionsExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TermsAndConditionsDeletePopupComponent,
        resolve: {
            termsAndConditions: TermsAndConditionsExtendedResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'TermsAndConditions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
