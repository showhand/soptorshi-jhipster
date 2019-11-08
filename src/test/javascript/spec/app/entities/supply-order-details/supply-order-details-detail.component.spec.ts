/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyOrderDetailsDetailComponent } from 'app/entities/supply-order-details/supply-order-details-detail.component';
import { SupplyOrderDetails } from 'app/shared/model/supply-order-details.model';

describe('Component Tests', () => {
    describe('SupplyOrderDetails Management Detail Component', () => {
        let comp: SupplyOrderDetailsDetailComponent;
        let fixture: ComponentFixture<SupplyOrderDetailsDetailComponent>;
        const route = ({ data: of({ supplyOrderDetails: new SupplyOrderDetails(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyOrderDetailsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SupplyOrderDetailsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplyOrderDetailsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.supplyOrderDetails).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
