/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { TrainingInformationAttachmentDetailComponent } from 'app/entities/training-information-attachment/training-information-attachment-detail.component';
import { TrainingInformationAttachment } from 'app/shared/model/training-information-attachment.model';

describe('Component Tests', () => {
    describe('TrainingInformationAttachment Management Detail Component', () => {
        let comp: TrainingInformationAttachmentDetailComponent;
        let fixture: ComponentFixture<TrainingInformationAttachmentDetailComponent>;
        const route = ({ data: of({ trainingInformationAttachment: new TrainingInformationAttachment(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [TrainingInformationAttachmentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TrainingInformationAttachmentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TrainingInformationAttachmentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.trainingInformationAttachment).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
