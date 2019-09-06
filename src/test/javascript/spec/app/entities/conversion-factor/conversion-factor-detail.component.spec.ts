/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { ConversionFactorDetailComponent } from 'app/entities/conversion-factor/conversion-factor-detail.component';
import { ConversionFactor } from 'app/shared/model/conversion-factor.model';

describe('Component Tests', () => {
    describe('ConversionFactor Management Detail Component', () => {
        let comp: ConversionFactorDetailComponent;
        let fixture: ComponentFixture<ConversionFactorDetailComponent>;
        const route = ({ data: of({ conversionFactor: new ConversionFactor(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ConversionFactorDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ConversionFactorDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ConversionFactorDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.conversionFactor).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
