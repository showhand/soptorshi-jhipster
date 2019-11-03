/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialAttachmentDetailComponent } from 'app/entities/commercial-attachment/commercial-attachment-detail.component';
import { CommercialAttachment } from 'app/shared/model/commercial-attachment.model';

describe('Component Tests', () => {
    describe('CommercialAttachment Management Detail Component', () => {
        let comp: CommercialAttachmentDetailComponent;
        let fixture: ComponentFixture<CommercialAttachmentDetailComponent>;
        const route = ({ data: of({ commercialAttachment: new CommercialAttachment(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialAttachmentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CommercialAttachmentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialAttachmentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.commercialAttachment).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
