/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplySalesRepresentativeDetailComponent } from 'app/entities/supply-sales-representative/supply-sales-representative-detail.component';
import { SupplySalesRepresentative } from 'app/shared/model/supply-sales-representative.model';

describe('Component Tests', () => {
    describe('SupplySalesRepresentative Management Detail Component', () => {
        let comp: SupplySalesRepresentativeDetailComponent;
        let fixture: ComponentFixture<SupplySalesRepresentativeDetailComponent>;
        const route = ({ data: of({ supplySalesRepresentative: new SupplySalesRepresentative(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplySalesRepresentativeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SupplySalesRepresentativeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplySalesRepresentativeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.supplySalesRepresentative).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
