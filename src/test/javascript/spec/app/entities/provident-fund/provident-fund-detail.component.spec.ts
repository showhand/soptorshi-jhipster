/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { ProvidentFundDetailComponent } from 'app/entities/provident-fund/provident-fund-detail.component';
import { ProvidentFund } from 'app/shared/model/provident-fund.model';

describe('Component Tests', () => {
    describe('ProvidentFund Management Detail Component', () => {
        let comp: ProvidentFundDetailComponent;
        let fixture: ComponentFixture<ProvidentFundDetailComponent>;
        const route = ({ data: of({ providentFund: new ProvidentFund(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ProvidentFundDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProvidentFundDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProvidentFundDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.providentFund).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
